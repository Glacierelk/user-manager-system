FROM nginx:latest
LABEL authors="Glacier-elk"

COPY ./dist /usr/share/nginx/html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]