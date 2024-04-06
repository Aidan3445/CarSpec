
# Car Spec API

Welcome to the Car Spec API! This API provides comprehensive information about cars and their specifications.

## API Base URL

The base URL for accessing the Car Spec API is:

`
https://car-spec.adaptable.app/
`

## Endpoints

### List Cars

Retrieve a list of cars based on optional query parameters.

- **Endpoint**: `/cars/`
- **Query Parameters**:
  - `name`: Filter cars by name (optional).
  - `year`: Filter cars by manufacturing year (optional).
  - `make`: Filter cars by manufacturer (optional).
- **Example Usage**: `cars/?year=2022&make=Toyota`

### Car Specifications

Retrieve detailed specifications of a specific car using its unique identifier.

- **Endpoint**: `/specs/:id`
- **Parameters**:
  - `id`: Unique identifier of the car.
- **Example Usage**: `/specs/11`
