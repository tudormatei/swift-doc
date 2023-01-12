import React, { Component } from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';  

import { Navbar, Home, NewDocument, About, Login, Register, Footer} from './components';
import './App.css'

const App = () => {
  return (
    <BrowserRouter>
        <div className="app">
          <Navbar />
          <Routes>
            <Route path="/home" element={<Home />} />
            <Route path="/new document" element={<NewDocument />} />
            <Route path="/about" element={<About />} />
            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<Register />} />
          </Routes>
          <Footer />
        </div>
    </BrowserRouter>
  )
}

export default App