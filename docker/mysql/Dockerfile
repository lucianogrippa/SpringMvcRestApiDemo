FROM mysql:5.7

LABEL eu.grippaweb.version="1.0.1-beta" \
	  eu.grippaweb.vendor="GRIPPAWEB EU" \
	  eu.grippaweb.release-date="2019-10-10" \
	  eu.grippaweb.version.is-production=""

ADD ./mysql.cnf /etc/mysql/conf.d/mysql.cnf
ADD ./dump.sql /docker-entrypoint-initdb.d