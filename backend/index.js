const express = require("express");
const cors = require("cors");
const env = require("dotenv");

const cars = require("./data/carData.json");

env.config();
const app = express();
app.listen(process.env.PORT || 1332, async () => {
    console.log(`Server running on port ${process.env.PORT || 1332}!`);
});

app.use(cors());
app.use(express.json());

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
