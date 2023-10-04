# Dockerfile
FROM node:18

WORKDIR /app

COPY ./package*.json ./
RUN npm install

COPY . .

EXPOSE 9999

ENV NODE_ENV=production

CMD ["node", "server.js" ]
