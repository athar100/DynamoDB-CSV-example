#DynamoDB REST APIs example

https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/DynamoDBLocal.DownloadingAndRunning.html

#Show Tables

aws dynamodb list-tables --endpoint-url http://localhost:8000

#Create Table

aws dynamodb create-table --attribute-definitions AttributeName=id,AttributeType=S --table-name Movie --key-schema AttributeName=id,KeyType=HASH --provisioned-throughput ReadCapacityUnits=1,WriteCapacityUnits=1 --region us-east-1 --output json --endpoint-url http://localhost:8000

#Delete Table

aws dynamodb delete-table --table-name Movie --endpoint-url http://localhost:8000

#Architecture

![architecture](architecture.png)

#ER Diagram

![er-diagram](er-diagram.png)