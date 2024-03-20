FROM public.ecr.aws/nginx/nginx:mainline-alpine3.18-perl

# Expose port 80 for web traffic
EXPOSE 8000

# Command to start Nginx when the container runs
CMD ["nginx", "-g", "daemon off;"]
