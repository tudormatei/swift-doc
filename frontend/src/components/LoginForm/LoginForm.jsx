import React from 'react'

import './LoginForm.css';
import { images } from '../../constants';

const LoginForm = () => {
  return (
    <form className="login w-100 d-flex flex-column justify-content-center align-items-center">
      <div class="mb-3">
          <label for="exampleInputEmail1" class="form-label">Email address</label>
          <input type="email" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp"/>
      </div>
      <div class="mb-3">
          <label for="exampleInputPassword1" class="form-label">Password</label>
          <input type="password" class="form-control" id="exampleInputPassword1"/>
      </div>
      <div class="mb-3 form-check">
          <input type="checkbox" class="form-check-input" id="exampleCheck1"/>
          <label class="form-check-label" for="exampleCheck1">Remember me</label>
      </div>
    <button type="submit" class="btn btn-primary">Login</button>
    <div class="mb-3 mt-3">
      Don't have an account? Register <a href="/register">here</a>
    </div>
    </form>
  )
}

export default LoginForm