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
import cars from './data/cars.json';

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
                        return value.includes(car[key]);
                    });
                } else {
                    // if value is a string
                    result = result.filter((car) => {
                        return car[key] === value;
                    });
                }
            }
        }

        // only return the main fields
        result = result.map((car) => {
            return {
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

app.get("/api/specs/", async (req, res) => {
    try {
        // get the car with the id from the query
        var id = req.query.id;
        if (!id) {
            return res.status(400).json({ error: "id is required" });
        }
        var car = cars.find((car) => {
            // use == instead of === because the id is a string from the url
            return car.id == id;
        });

        // if the car is not found
        if (!car) {
            return res.status(404).json({ error: "Car not found" });
        }

        return res.json(car.specs);
    } catch (err) {
        console.error(err);
        res.status(500).json({ error: "Server error" });
    }
});
//#endregion
export default app;
