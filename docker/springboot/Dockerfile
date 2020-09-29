FROM openjdk:11

USER root

RUN apt-get update && apt-get install vim git -y  

#RUN mkdir -p env && mkdir -p exec && mkdir -p log
RUN mkdir -p /root/app && chmod 7775 /root/app

RUN cd /root/app

ADD ./scanner-jar.sh /root/app

WORKDIR /root/app

RUN sed -i -e 's/\r$//' /root/app/scanner-jar.sh

RUN chmod 7775 /root/app/scanner-jar.sh

CMD ["/root/app/scanner-jar.sh"]
