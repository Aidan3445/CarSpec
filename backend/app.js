// Copyright 2021 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

import express from 'express';
import { pinoHttp } from './utils/logging.js';
import cars from './data/carData.js';

const app = express();

// Use request-based logger for log correlation
app.use(pinoHttp);

app.get("/api/cars", async (req, res) => {
    try {
        var result = cars;

        // Filtering the cars based on the query parameters
        for (const key in req.query) {
            if (req.query.hasOwnProperty(key)) {
                const value = req.query[key];
                // if value is an array
                if (Array.isArray(value)) {
                    result = result.filter((car) => {
                        return value.find((v) => car[key] == v) !== undefined;
                    });
                } else {
                    // if value is a string
                    result = result.filter((car) => {
                        return car[key] == value;
                    });
                }
            }
        }

        // only return the main fields
        result = result.map((car) => {
            return {
                id: car.id,
                name: car.name,
                make: car.make,
                year: car.year,
                img: car.imgURL,
            };
        });

        // return the result
        return res.json(result);
    } catch (err) {
        console.error(err);
        res.status(500).json({ error: "Server error" });
    }
});

app.get("/api/cars/:id", async (req, res) => {
    try {
        // find the car with the id, use == to match string and number
        var id = req.params.id;
        var car = cars.find((car) => car.id == id);

        // if the car is not found
        if (!car) {
            return res.status(404).json({ error: "Car not found" });
        }

        return res.json(car);
    } catch (err) {
        console.error(err);
        res.status(500).json({ error: "Server error" });
    }
});

app.get("/api/specs/:id", async (req, res) => {
    try {
        // find the car with the id, use == to match string and number
        var id = req.params.id;
        var car = cars.find((car) => car.id == id);

        // if the car is not found
        if (!car) {
            return res.status(404).json({ error: "Car not found" });
        }

        return res.json({ id: car.id, ...car.specs });
    } catch (err) {
        console.error(err);
        res.status(500).json({ error: "Server error" });
    }
});

app.get("/api/login", async (req, res) => {
    try {
        // check if the username and password are correct
        var username = req.query.username;
        var password = req.query.password;

        if (username === "admin" && password === "password") {
            return res.json({ success: true });
        } else {
            return res.status(401).json({ error: "Invalid username or password" });
        }
    } catch (err) {
        console.error(err);
        res.status(500).json({ error: "Server error" });
    }
});

app.get("/api/login/hint", async (_, res) => {
    try {
        return res.json({ hint: "Username: admin\nPassword: password" });
    } catch (err) {
        console.error(err);
        res.status(500).json({ error: "Server error" });
    }
});
//#endregion
export default app;
