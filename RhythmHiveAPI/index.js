const auth = require('./router/auth');
const express =  require('express');
const mongoose = require('mongoose');

const bodyParser = require('body-parser');

const app = express();

mongoose.connect('mongodb://localhost/rhythmhive')
.then(() => console.log('Connected to mongoDB...'))
.catch(err => console.log('Error connecting to mongoDB...'));

const port = process.env.PORT || 3000;
app.listen(port, () => console.log(`Listening on port ${port}...`));

// app.use(bodyParser.json());
// app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json({limit: '50mb'}));
app.use(bodyParser.urlencoded({limit: '50mb', extended: true}));
//app.use(express.json());
app.use('/', auth);

//et url = "http://ws.audioscrobbler.com/2.0/?method=track.search&track=let+her+go&api_key=692dc2e7b91174c909809cf3d07737e4&format=json"