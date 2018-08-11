cd target/sam
aws s3 sync demo-0.0.1-SNAPSHOT.jar s3://s3circleci/ --delete
