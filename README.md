# dumbbell-backend
Backend for dumbbell application.

## Getting started

1. Clone de repo
2. Import it using gradle in Intellij
3. Populate .env file
4. Configure your run environment
    1. Got To edit configuration
    2. Select `DumbbellBackendApplication`
    3. Paste all dev variable from .env file to `environment variables` section
    4. Add `dev` in profile section
5. Run `Docker dev stack` to set up a local database
6. Run the application

## Run in alpha mode
1. Execute `docker/Build image` task in gradle
2. Run `Docker alpha` in intellij