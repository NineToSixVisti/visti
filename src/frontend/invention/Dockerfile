# Dockerfile
FROM node:18

WORKDIR /app

COPY ./package*.json ./
RUN npm install

COPY . .

EXPOSE 9999

CMD [ "node", "server.js" ]
