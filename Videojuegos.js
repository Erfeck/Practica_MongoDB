use dbVideojuegos

db.desarrollador.insertMany([
    {
        nombre: "Mojang",
        email: "mojang@gmail.com",
        experiencia: 20,
        tipo_desarrollo: "Estudio Indie"
    },
    {
        nombre: "Naughty Dog",
        email: "naughtydog@gmail.com",
        experiencia: 25,
        tipo_desarrollo: "Estudio AAA"
    },
    {
        nombre: "Supercell",
        email: "supercell@gmail.com",
        experiencia: 12,
        tipo_desarrollo: "Estudio de Nicho"
    },
    {
        nombre: "CD Projekt Red",
        email: "cdprojektred@gmail.com",
        experiencia: 18,
        tipo_desarrollo: "Estudio AAA"
    },
    {
        nombre: "Team Cherry",
        email: "teamcherry@gmail.com",
        experiencia: 8,
        tipo_desarrollo: "Estudio Indie"
    },
    {
        nombre: "FromSoftware",
        email: "fromsoftware@gmail.com",
        experiencia: 15,
        tipo_desarrollo: "Estudio AAA"
    }
]);
db.videojuego.insertMany([
    {
        titulo: "Minecraft",
        genero: "Simulación",
        precio: 19.99,
        plataforma: "PC",
        fecha_lanzamiento: "17-05-2009",
        desarrolladores: [ObjectId("67c0b523acd6fe356e072f92"), ObjectId("67c0b523acd6fe356e072f93")]
    },
    {
        titulo: "The Last of Us",
        genero: "Acción",
        precio: 39.99,
        plataforma: "Playstation",
        fecha_lanzamiento: "14-06-2013",
        desarrolladores: [ObjectId("67c0b523acd6fe356e072f93"), ObjectId("67c0b523acd6fe356e072f94")]
    },
    {
        titulo: "Clash Royale",
        genero: "Estrategia",
        precio: 20.99,
        plataforma: "PC",
        fecha_lanzamiento: "02-03-2016",
        desarrolladores: [ObjectId("67c0b523acd6fe356e072f94"), ObjectId("67c0b523acd6fe356e072f95")]
    },
    {
        titulo: "The Witcher 3: Wild Hunt",
        genero: "Rol",
        precio: 59.99,
        plataforma: "PC",
        fecha_lanzamiento: "19-05-2015",
        desarrolladores: [ObjectId("67c0b523acd6fe356e072f95"), ObjectId("67c0b523acd6fe356e072f96")]
    },
    {
        titulo: "Hollow Knight",
        genero: "Aventura",
        precio: 14.99,
        plataforma: "PC",
        fecha_lanzamiento: "24-02-2017",
        desarrolladores: [ObjectId("67c0b523acd6fe356e072f96"), ObjectId("67c0b523acd6fe356e072f97")]
    },
    {
        titulo: "Sekiro: Shadows Die Twice",
        genero: "Terror",
        precio: 59.99,
        plataforma: "Playstation",
        fecha_lanzamiento: "22-03-2019",
        desarrolladores: [ObjectId("67c0b523acd6fe356e072f97"), ObjectId("67c0b523acd6fe356e072f92")]
    }
]);
db.puntuacion.insertMany([
    {
        videojuego: ObjectId("67c0b6683eccb02541072f97"),
        fuente: "IGN",
        puntuacion: 9.5,
        fecha_puntuacion: "17-05-2010"
    },
    {
        videojuego: ObjectId("67c0b6683eccb02541072f97"),
        fuente: "IGN",
        puntuacion: 9.5,
        fecha_puntuacion: "17-05-2009"
    },
    {
        videojuego: ObjectId("67c0b6683eccb02541072f97"),
        fuente: "GameSpot",
        puntuacion: 8.8,
        fecha_puntuacion: "18-05-2009"
    },
    {
        videojuego: ObjectId("67c0b6683eccb02541072f97"),
        fuente: "Metacritic",
        puntuacion: 9.2,
        fecha_puntuacion: "19-05-2009"
    },
    {
        videojuego: ObjectId("67c0b6683eccb02541072f98"),
        fuente: "IGN",
        puntuacion: 9.9,
        fecha_puntuacion: "14-06-2013"
    },
    {
        videojuego: ObjectId("67c0b6683eccb02541072f98"),
        fuente: "Game Informer",
        puntuacion: 9.75,
        fecha_puntuacion: "15-06-2013"
    },
    {
        videojuego: ObjectId("67c0b6683eccb02541072f98"),
        fuente: "Metacritic",
        puntuacion: 9.3,
        fecha_puntuacion: "16-06-2013"
    },
    {
        videojuego: ObjectId("67c0b6683eccb02541072f99"),
        fuente: "Pocket Gamer",
        puntuacion: 8.5,
        fecha_puntuacion: "03-03-2016"
    },
    {
        videojuego: ObjectId("67c0b6683eccb02541072f99"),
        fuente: "TouchArcade",
        puntuacion: 9.2,
        fecha_puntuacion: "04-03-2016"
    },
    {
        videojuego: ObjectId("67c0b6683eccb02541072f99"),
        fuente: "Metacritic",
        puntuacion: 8.7,
        fecha_puntuacion: "05-03-2016"
    },
    {
        videojuego: ObjectId("67c0b6683eccb02541072f9a"),
        fuente: "IGN",
        puntuacion: 9.8,
        fecha_puntuacion: "20-05-2015"
    },
    {
        videojuego: ObjectId("67c0b6683eccb02541072f9a"),
        fuente: "GameSpot",
        puntuacion: 9.5,
        fecha_puntuacion: "21-05-2015"
    },
    {
        videojuego: ObjectId("67c0b6683eccb02541072f9a"),
        fuente: "Metacritic",
        puntuacion: 9.6,
        fecha_puntuacion: "22-05-2015"
    },
    {
        videojuego: ObjectId("67c0b6683eccb02541072f9b"),
        fuente: "IGN",
        puntuacion: 9.1,
        fecha_puntuacion: "25-02-2017"
    },
    {
        videojuego: ObjectId("67c0b6683eccb02541072f9b"),
        fuente: "Game Informer",
        puntuacion: 9.2,
        fecha_puntuacion: "26-02-2017"
    },
    {
        videojuego: ObjectId("67c0b6683eccb02541072f9b"),
        fuente: "Metacritic",
        puntuacion: 9.4,
        fecha_puntuacion: "27-02-2017"
    },
    {
        videojuego: ObjectId("67c0b6683eccb02541072f9c"),
        fuente: "IGN",
        puntuacion: 9.7,
        fecha_puntuacion: "23-03-2019"
    },
    {
        videojuego: ObjectId("67c0b6683eccb02541072f9c"),
        fuente: "GameSpot",
        puntuacion: 9.5,
        fecha_puntuacion: "24-03-2019"
    },
    {
        videojuego: ObjectId("67c0b6683eccb02541072f9c"),
        fuente: "Metacritic",
        puntuacion: 9.6,
        fecha_puntuacion: "25-03-2019"
    }
]);
