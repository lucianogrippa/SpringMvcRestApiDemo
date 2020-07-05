-- db sprindemo
CREATE DATABASE IF NOT EXISTS restapidemo CHARACTER SET = 'utf8'
  COLLATE = 'utf8_general_ci';

USE restapidemo;

SET autocommit =0;
set FOREIGN_KEY_CHECKS =0;

START TRANSACTION;

-- lista utenti
DROP TABLE IF EXISTS users;
CREATE TABLE IF NOT EXISTS users (
 id bigint(20) AUTO_INCREMENT NOT NULL PRIMARY KEY,
 firstname VARCHAR(500) NOT NULL,
 lastname VARCHAR(500) NOT NULL,
 username VARCHAR(500) NOT NULL,
 secret VARCHAR(500) NOT NULL,
 active  TINYINT(1) NOT NULL DEFAULT 1,
 email VARCHAR(1000) NOT NULL,
 creationtimestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 lastupdate DATETIME DEFAULT NULL,
 lastaccess  DATETIME DEFAULT NULL,
 
 UNIQUE INDEX usernameindex (username)
) CHARACTER SET = 'utf8'
  COLLATE = 'utf8_general_ci' , ENGINE=INNODB;

-- lista i ruoli
DROP TABLE IF EXISTS roles;
CREATE TABLE IF NOT EXISTS roles (
 id bigint(20) AUTO_INCREMENT NOT NULL PRIMARY KEY,
 name VARCHAR(500) NOT NULL,
 code VARCHAR(500) NOT NULL
)
CHARACTER SET = 'utf8'
COLLATE = 'utf8_general_ci' , ENGINE=INNODB;
 
-- tabella utenti ruolo
DROP TABLE IF EXISTS usersroles;
CREATE TABLE IF NOT EXISTS usersroles (
 userid bigint(20) NOT NULL,
 roleid bigint(20) NOT NULL,
 
 PRIMARY KEY (userid,roleid),
 
 FOREIGN KEY usersidfk (userid) REFERENCES users(id) ON UPDATE CASCADE ON DELETE CASCADE,
 FOREIGN KEY roleidfk (roleid) REFERENCES roles(id) ON UPDATE CASCADE ON DELETE CASCADE
)
CHARACTER SET = 'utf8'
COLLATE = 'utf8_general_ci', ENGINE=INNODB;

-- vista utente ruolo
CREATE OR REPLACE VIEW view_usersoles AS
SELECT 
 u.id AS userid,
 u.firstname AS firstname,
 u.lastname AS lastname,
 u.username AS username,
 u.secret AS secret,
 u.active  AS active,
 u.email AS email,
 u.creationtimestamp AS creationdate,
 u.lastupdate AS lastupdate,
 u.lastaccess AS lastaccess,
 r.id AS roleid,
 r.code AS rolecode
FROM users u  JOIN roles r JOIN usersroles us
ON  u.id=us.userid AND r.id = us.roleid;

-- store procedure crea o update utente
DELIMITER $

DROP PROCEDURE IF EXISTS sp_createOrUpdateUser $
CREATE PROCEDURE sp_createOrUpdateUser (
	IN iduser bigint(20),
    IN userfirstname VARCHAR(500),
    IN userlastname VARCHAR(500),
    -- username
    IN uid VARCHAR(500),
    IN usersecret VARCHAR(500),
    IN useractive  TINYINT(1),
    IN useremail VARCHAR(1000),
    -- roleid
    IN rid bigint(20)
) 
BEGIN
	
	 DECLARE userexists TINYINT(1);
	 DECLARE rolexists TINYINT(1);
	 DECLARE usersrolexists TINYINT(1);
	 DECLARE newuserid BIGINT(20);
	 DECLARE EXIT HANDLER FOR SQLEXCEPTION
		BEGIN
			SELECT "in gestione errore " AS error;
			GET DIAGNOSTICS CONDITION 1 @sqlstate = RETURNED_SQLSTATE, 
			 @errno = MYSQL_ERRNO, @text = MESSAGE_TEXT;
			SET @full_error = CONCAT("ERROR ", @errno, " (", @sqlstate, "): ", @text);
			SELECT @full_error;
		
			ROLLBACK;
		END;
	-- set defaults --
	 set userexists =0;
	 set rolexists =0;
	 set usersrolexists =0;
	 set newuserid =0;
	

	 SELECT if(count(*) >0 ,1,0) INTO userexists FROM users WHERE id=iduser;
	 SELECT if(count(*) >0 ,1,0) INTO rolexists FROM roles WHERE id=rid;
	 SELECT if(count(*) >0 ,1,0) INTO usersrolexists FROM usersroles WHERE userid=iduser AND roleid=rid;
	
	 IF rolexists THEN
	
		 SELECT concat("stato utente-> ",IF(userexists=1,'exists','not exists')) AS info;
		 SELECT concat("stato ruolo-> ",IF(rolexists=1,'exists','not exists')) AS info;
	
		 START TRANSACTION;
		
		 IF userexists THEN
			 	-- update
			 UPDATE users SET firstname=userfirstname,
			                  lastname=userlastname,
			                  username=uid,
			                  secret=usersecret,
			                  email=useremail,
			                  active=useractive,
			                  lastupdate=now()
			    WHERE id=iduser;
	
			   
		ELSE
		  SELECT concat("inserisci utente nel ruolo userid:roleid ",idUser,":",rid) AS info;
			-- insert
		  INSERT INTO users (firstname,lastname,username,secret,email,active,lastupdate)
		  VALUES (userfirstname,userlastname,uid,usersecret,useremail,useractive,now());
		 
		  -- ottieni lì'ultimo id inserito
		   SELECT LAST_INSERT_ID() INTO newuserid FROM users limit 1;
		   SELECT concat("creato nuovo utente id-> ",newuserid) AS info;
		  
		   IF newuserid >0 THEN
		    set iduser= newuserid;
		    SET userexists = 1;
		   END IF;
		END IF;
		
	    -- se lo user role non nesiste crealo
	    IF NOT usersrolexists THEN
	     INSERT INTO usersroles (userid,roleid) VALUES (iduser,rid);
	      SET usersrolexists=1;
	    END IF;
	    
	   IF userexists then
	    -- inserisci in ruoli utenti
		COMMIT;
	 	SELECT concat("creato nuovo utente e aggiunto al ruolo specificato utente:ruolo ",iduser,":",rid) AS error;
	  ELSE
	    ROLLBACK;
	     SELECT "non e' stato possibile creare l' associazione utente ruolo" AS error;
	  END IF;
   ELSE
   	 -- non esiste il ruolo
   	  SELECT concat("errore l'id del ruolo non esiste per roleid: ",rid) AS error;
   END IF;


END $

DELIMITER ;


-- crea i ruoli
INSERT INTO roles (id,name,code) VALUES (1,"Amministratore","ROLE_ADMIN"),
									    (2,"Utente avanzato","ROLE_SUPER_USER"),
										(3,"Utente","ROLE_USER"),
									 	(4,"Ospite","ROLE_GUEST");

-- crea due utenti 
CALL sp_createOrUpdateUser(-1,"Luciano","Grippa","lgrippa","48df8b3e62340b9cf63040d3318c8809",1,"lgrippa75@gmail.com",1);
CALL sp_createOrUpdateUser(-1,"Mary","Grippa","mgrippa","371bee64fc851f896122d88b8678ca36",1,"luciano_grippa@hotmail.com",2);
CALL sp_createOrUpdateUser(-1,"Gino","Grippa","ggrippa","371bee64fc851f896122d88b8678ca36",1,"luciano_grippa@yahoo.com",3);
CALL sp_createOrUpdateUser(-1,"Giada","Grippa","gigrippa","a59ebcabdac43122aea847bc0eb7e8fb",1,"info@grippaweb.eu",4);

COMMIT;
SET autocommit =1;
set FOREIGN_KEY_CHECKS =1;

GRANT ALL PRIVILEGES ON restapidemo.* TO 'user'@'%';
GRANT ALL PRIVILEGES ON restapidemo.* TO 'user'@'localhost';

