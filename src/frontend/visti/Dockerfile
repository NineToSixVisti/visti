#  Dockerfile
FROM node:18 as builder

WORKDIR /app

COPY ./package*.json ./

RUN /usr/local/bin/node --max-old-space-size=4096 /usr/local/lib/node_modules/npm/bin/npm-cli.js install

COPY . .

RUN npm run build

FROM nginx:latest 

COPY ./fullchain.pem /etc/letsencrypt/live/visti-story.com/fullchain.pem
COPY ./privkey.pem /etc/letsencrypt/live/visti-story.com/privkey.pem

COPY ./nginx.conf /etc/nginx/conf.d/react.conf

COPY --from=builder /app/build /usr/share/nginx/html

EXPOSE 3000

CMD ["nginx", "-g", "daemon off;"]

