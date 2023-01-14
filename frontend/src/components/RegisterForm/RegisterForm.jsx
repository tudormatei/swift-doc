import React from 'react'

import './RegisterForm.css';
import { images } from '../../constants';

const RegisterForm = () => {
  return (
    <form className="register d-flex flex-column justify-content-center align-items-center">
    <div class="mb-3">
        <label for="exampleInputEmail1" class="form-label">Email address</label>
        <input type="email" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp"/>
    </div>
    <div class="mb-3">
        <label for="exampleInputPassword1" class="form-label">Password</label>
        <input type="password" class="form-control" id="exampleInputPassword1"/>
    </div>
    <button type="submit" class="btn btn-primary">Register</button>
    </form>
  )
}

export default RegisterForm