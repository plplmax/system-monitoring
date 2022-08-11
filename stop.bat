powershell "docker-compose down"
powershell "docker rmi -f $(docker images system-monitoring -q)"
