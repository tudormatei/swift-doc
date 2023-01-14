import React from 'react'

import './DataFields.css';
import { images } from '../../constants';

const DataFields = () => {
  return (
    <form>
        <div className="datafields w-100 d-flex flex-column justify-content-center align-items-center">
            <div class="w-50 mb-5 text-center ">
                <h1>Your Profile Info</h1>
            </div>
            <div class="mb-3 w-25">
                <label for="exampleInputName1" class="form-label">First Name</label>
                <input type="text" class="form-control" id="exampleInputName1"/>
            </div>
            <div class="mb-3 w-25">
                <label for="exampleInputName1" class="form-label">Last Name</label>
                <input type="text" class="form-control" id="exampleInputName1"/>
            </div>
            <div class="mb-3 w-25">
                <label for="exampleInputName1" class="form-label">Adress</label>
                <input type="text" class="form-control" id="exampleInputName1"/>
            </div>
            <div class="mb-3 w-25">
                <label for="exampleInputName1" class="form-label">City</label>
                <input type="text" class="form-control" id="exampleInputName1"/>
            </div>
            <div class="mb-3 w-25">
                <label for="exampleInputName1" class="form-label">County</label>
                <input type="text" class="form-control" id="exampleInputName1"/>
            </div>
            <div class="mb-3 w-25">
                <label for="exampleInputName1" class="form-label">Zip Code</label>
                <input type="text" class="form-control" id="exampleInputName1"/>
            </div>
            <div class="mb-3 w-25">
                <label for="exampleInputName1" class="form-label">Country</label>
                <input type="text" class="form-control" id="exampleInputName1"/>
            </div>
            <div class="mb-3 w-25">
                <label for="exampleInputName1" class="form-label">Phone Number</label>
                <input type="text" class="form-control" id="exampleInputName1"/>
            </div>
            <div class="mb-3 w-25">
                <label for="exampleInputName1" class="form-label">C.I. Number</label>
                <input type="text" class="form-control" id="exampleInputName1"/>
            </div>
            <div class="mb-3 w-25">
                <label for="exampleInputEmail1" class="form-label">Email address</label>
                <input type="email" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp"/>
            </div>
            <div class="mb-3 w-25">
                <label for="exampleInputPassword1" class="form-label">Password</label>
                <input type="password" class="form-control" id="exampleInputPassword1"/>
            </div>
            <button type="submit" class="btn btn-primary">Update</button>
        </div>
    </form>
  )
}

export default DataFields