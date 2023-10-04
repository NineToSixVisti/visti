#  Dockerfile
FROM node:18 as builder

WORKDIR /app

COPY ./package*.json ./
COPY ./server.js ./

RUN npm install

COPY . .

RUN npm run build

FROM nginx:latest 

COPY --from=builder /app/build /usr/share/nginx/html/invention

EXPOSE 9999

