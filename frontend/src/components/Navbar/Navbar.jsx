import React from 'react'

import './Navbar.css';
import { images } from '../../constants';

const Navbar = () => {
  return (
    <nav class="navbar navbar-expand-lg navbar-light py-3 px-5 fixed-top">
        <div class="container-fluid">
        <a class="navbar-brand" href="/"><img src={images.logo} alt="Bootstrap" width="176" height="36"/></a>
          <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
          </button>
          <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mx-auto mb-2 mb-lg-0">
              <li class="nav-item">
                <a class="nav-link" href="/home">Home</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="/new document">New Document</a>
              </li>
              {/* <li class="nav-item">
                <a class="nav-link" href="/collection">Collection</a>
              </li> */}
              <li class="nav-item">
                <a class="nav-link" href="/profile">Profile</a>
              </li>
            </ul>
            <a href="/login"><button class="btn btn-success text-dark" type="submit">Login</button></a>
          </div>
        </div>
      </nav>
  )
}

export default Navbar