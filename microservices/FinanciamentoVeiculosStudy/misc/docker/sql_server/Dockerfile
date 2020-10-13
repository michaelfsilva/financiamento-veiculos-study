FROM mcr.microsoft.com/mssql/server

ENV CHECK_POLICY=OFF
ENV ACCEPT_EULA=Y
ENV SA_PASSWORD=Sql.server
ENV MSSQL_PID=Developer

COPY setup_scripts /setup_scripts/

RUN ( /opt/mssql/bin/sqlservr --accept-eula & ) | grep -q "Service Broker manager has started" \
    && /opt/mssql-tools/bin/sqlcmd -S localhost -U SA -P ${SA_PASSWORD} -i /setup_scripts/000_init-user-db.sql \
    && pkill sqlservr

CMD ["/opt/mssql/bin/sqlservr"]
