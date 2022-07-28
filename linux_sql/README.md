# Linux Cluster Monitoring Agent
This project is under development. Since this project follows the GitFlow, the final work will be merged to the main branch after Team Code Team.

# Introduction


The technologies used in this project are bash, docker, git, gitFlow, linux, jrd(remote desktop), gcp(google cloud platform), postgresSQL.
# Quick Start
The following are the steps to setup the program.
- Creating a database from `psql_docker.sh`:
```
bash ./script/psql_docker.sh create [db_username] [db_password]
```
- Start a psql instance using `psql_docker.sh`
``` 
bash ./scripts/psql_docker.sh start 
```
- Create tables using ddl.sql
``` 
bash ./sql/ddl.sql 
```
- Insert hardware specs data into the DB using host_info.sh
``` 
bash ./scripts/host_info.sh [psql_host] [psql_port] [db_name] [psql_user] [psql_password] 
```
- Insert hardware usage data into the DB using host_usage.sh
``` 
bash ./scripts/host_usage.sh [psql_host] [psql_port] [db_name] [psql_user] [psql_password] 
```
- Crontab setup
` contrab -e ` to edit crontab jobs
press 'i' to start inserting.
`* * * * * ` insert 5 asteriks before the bash command to repeat every minute.
``` 
bash [full_file_path]/host_usage.sh [psql_host] [psql_port] [db_name] [psql_user] [psql_password] > /tmp/host_usage.log
```
-The above command will add this to crontab jobs and it should look like as follows :
``` 
* * * * * bash /home/centos/dev/jrvs/bootcamp/linux_sql/host_agent/scripts/host_usage.sh localhost 5432 host_agent postgres password > /tmp/host_usage.log 
``` 
# Implemenation
The implementation of the project are in the following steps:- 
- 1. Create a google cloud platform to setup the virtual machine with CentOS 7 which is going to be used to develop the project.
- 2. After the VM i.e(virtual machine) install the VNC Viewer to run the JRD(Jarvis Remote Desktop)
- 3. Initialize the docker and postgresSQL for the database.
- 4. Use crontab to schedule the script of host_usage to store every minute in the database.
- 5. Validate the sql queries.

**Note:-** The google cloud platform will charge your credit/debit card for using compute engine for virtual machine.

## Architecture
Draw a cluster diagram with three Linux hosts, a DB, and agents (use draw.io website). Image must be saved to the `assets` directory.

![diagram](./assets/diagram.png)

## Scripts
Shell script description
### psql_docker.sh 
- Create, start , or stop a psql_docker.sh instance `bash ./script/psql_docker.sh create [db_username] [db_password]`
### host_info.sh 
- The host info store data related to hardware specification in the database `bash ./scripts/host_info.sh [psql_host] [psql_port] [db_name] [psql_user] [psql_password]`
### host_usage.sh
- The host usage store data related to system usage in the database table `./scripts/host_usage.sh [psql_host] [psql_port] [db_name] [psql_user] [psql_password]`
### crontab 
- The crontab command creates a crontab file containing commands and instructions for the cron daemon to execute.
### queries.sql
This script contains queries to solve the following business cases:

- 1.Group hosts by CPU number and sort by their memory size in descending order
- 2.Average used memory in percentage over 5 mins interval for each host
- 3.Detecting host failure

## Database Modeling
Describe the schema of each table using markdown table syntax (do not put any sql code)
- `host_info`
| Properties  | Type | Constraints  | 
| ------------- | ------------- | ------------- | 
|  id | Serial  | PRIMARY KEY  | 
| hostname | varchaar  | UNIQUE KEY , NOT NULL  | 
| cpu_number  | integer  | NOT NULL  | 
| cpu_architecture  | varchar  |  NOT NULL | 
| cpu_model | varchar  | NOT NULL  | 
| cpu_mhz  | numberic(10,3)  | NOT NULL  | 
| L2_cache  | integer  | NOT NULL  | 
| total_mem  | integer  | NOT NULL  | 
| timestamp  | timestamp | NOT NULL  |
		
host_usage
|Label	Name	Type	Nullable	Default	Comment
|timestamp	timestamp	TIMESTAMP	false		
|host_id	host_id	INT	false	FOREIGN KEY	pointing to 'id' primary key of host_info table
|memory_free	memory_free	INT	false		
|cpu_idle	cpu_idle	INT	false		
|cpu_kernel	cpu_kernel	INT	false		
|disk_io	disk_io	INT	false		
|disk_available	disk_available	INT	false		

- `host_usage`
| Properties  | Type | Constraint |
| ------------- | ------------- | ------------- |
| timestamp  | timestamp  | NOT NULL  |
| host_id  | SERIAL | FOREIGN KEY , NOT NULL  |
| memory_free  | integer | NOT NULL  |
| cpu_idle  | integer  | NOT NULL  |
| cpu_kernel  | smallint  | NOT NULL  |
| disk_io  | smallint  | NOT NULL  |
| disk_available  | integer  | NOT NULL  |
# Test
I tested the bash scripts and sql queries in VM on the GCP. The queries were tested in the intelliJ and terminal.
The result of this testing was that every script and queries ran successfully according to their specifications.

# Deployment
Github was used to manage the project different feature branches which were pushed through git commands.
Crontab was used to create jobs that populate the host_usage table every minute.
Docker was used to manage PSQL database 
# Improvements
Write at least three things you want to improve 
e.g. 
- handle hardware update 
- try to make more automation
- Analysing the data more user friendly to show it in a graph or chart.