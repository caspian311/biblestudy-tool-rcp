mysqldump --compact --extended-insert=false \
	--no-create-db --no-create-info \
	biblestudy BIBLE -u root -proot > data.sql

mysqldump --no-data --no-create-db \
	biblestudy BIBLE -u root -proot > create.sql
