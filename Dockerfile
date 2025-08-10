FROM newrelic/infrastructure:latest

# Copy your config file into container
COPY /newrelic/newrelic-infra.yml /etc/newrelic-infra.yml