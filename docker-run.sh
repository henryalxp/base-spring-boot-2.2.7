mvn clean package -Pprod
docker build -t aflac-demo .
docker stop aflac-demo
docker rm aflac-demo
docker run -d -p 8080:8080 -t --name aflac-demo aflac-demo