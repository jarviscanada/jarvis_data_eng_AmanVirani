#! /bin/bash

#CLI arguments
cmd=$1
db_username=$2
db_password=$3
container="jrvs-psql"
#Start docker
sudo systemctl status docker || systemctl start docker

#get latest postgres image
docker pull postgres

#check container status
docker container inspect jrvs-psql
container_status=$?

#User switch case to handle create|stop|start options
case $cmd in
  create)

  # Check if the container is already created
  if [ $container_status -eq 0 ]; then
		echo 'Container already exists'
		exit 1
	fi

  #Check if user input the username and password
  if [ $# -ne 3 ]; then
    echo 'Create requires username and password'
    echo 'use ./scripts/psql_docker.sh start|stop|create [db_username][db_password]'
    exit 1
  fi

  #Create container
	docker volume create pgdata
	docker run --name $container -e POSTGRES_USER=$db_username -e POSTGRES_PASSWORD=$db_password -d -v pgdata:/var/lib/postgresql/data -p 5432:5432 postgres:9.6-alpine
	exit $?
	;;

  start|stop)
  #check instance status; exit 1 if container has not been created
  if [ $container_status -ne 0 ]; then
    echo 'Container has not been created'
    echo 'use ./scripts/psql_docker.sh start|stop|create [db_username][db_password]'
    exit 1
  fi


  #Start or stop the container
	docker container $cmd $container
	exit $?
	;;

  *)
	echo 'Illegal command'
	echo 'Commands: start|stop|create'
	exit 1
	;;
esac
