NAME='sql-server'

build:
	docker build -t $(NAME) .

debug:
	docker run -it --rm --entrypoint=/bin/bash $(NAME)

run:
	docker run -d --name $(NAME) -p 1433:1433 $(NAME)

