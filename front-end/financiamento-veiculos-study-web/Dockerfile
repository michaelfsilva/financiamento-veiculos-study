#stage 1

FROM node:alpine AS veiculos-build

#defines dir
WORKDIR /app

#copy everything to app folder
COPY . .

#generate the dist folder
RUN npm install && npm run build

#stage 2

#base nginx image
FROM nginx:alpine

#copy the dist static files from application
COPY --from=veiculos-build /app/dist/financiamento-veiculos-study-web /usr/share/nginx/html

#port 80
EXPOSE 80
