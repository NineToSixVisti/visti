# Dockerfile
FROM node:18

WORKDIR /app

COPY ./package*.json ./
RUN npm install

COPY . .

EXPOSE 9999

CMD [ "NODE_ENV=production","node", "server.js" ]
