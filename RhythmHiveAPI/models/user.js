const Joi = require('joi');
const mongoose = require('mongoose');
const userSchema = new mongoose.Schema({
    name:{
        type:String,
        required: true,
    },
    username:{
        type:String,
        required: true,
        unique: true
    },
    email:{
        type: String,
        required: true,
        unique: true
    },
    password: {
        type: String,
        required: true
    },
    verification: {
        type: String
    }
});

function validateUser(user) {
    const schema = {
        name: Joi.string().min(1).max(50).required(),
        username: Joi.string().min(1).max(255).required(),
        email: Joi.string().min(5).max(255).required(),
        password: Joi.string().min(5).max(255).required()
    };

    return Joi.validate(user, schema);
}

const User = mongoose.model('User', userSchema);

exports.User = User;
exports.validate = validateUser;