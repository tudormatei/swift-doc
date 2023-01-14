import React from 'react'

import './Footer.css';

const Footer = () => {
  return (
    <div className="app__footer fixed-bottom">
      <div class="text-center links">
        <a class="btn btn-outline-dark btn-floating m-1" target="_blank" href="mailto:tudormatei010@gmail.com" role="button"><i class="bi bi-envelope"></i></a>
        <a class="btn btn-outline-dark btn-floating m-1" target="_blank" href="https://www.linkedin.com/in/tudor-matei-324269208/" role="button"><i class="bi bi-linkedin"></i></a>
        <a class="btn btn-outline-dark btn-floating m-1" target="_blank" href="https://github.com/tudormatei" role="button"><i class="bi bi-github"></i></a>
      </div>
      <div class="copyright text-center">© 2023 Tudor Matei</div>
    </div>
  )
}

export default Footer