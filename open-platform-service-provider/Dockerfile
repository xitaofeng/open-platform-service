FROM registry.elecredit.com/openjdk:8
COPY ./target/service-provider-assembly.tar.gz /usr/local/
WORKDIR /usr/local/
RUN tar zxvf service-provider-assembly.tar.gz
CMD ["/bin/sh", "/usr/local/service-provider/bin/start.sh"]