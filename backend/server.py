import os

import numpy as np
from flask import (Flask, Response, jsonify, request, send_file,
                   send_from_directory)
from PyPDF2 import PdfReader

app = Flask(__name__)

@app.route('/')
def index():
    return '<h1>FLASK APP IS RUNNING!</h1>'


@app.route('/api/pass', methods=['POST'])
def predict():
    try:
        pdf_file = request.files['doc']
        pdf_name = pdf_file.filename
        save_path='.//backend//documents//' + pdf_name

        #Saves file
        pdf_file.save(save_path)
        
        reader = PdfReader(save_path)
        page = reader.pages[0]
        print(page.extract_text())

        return "Sucessfully sent pdf file..."

    except Exception as e:
        app.logger.info("Error occurred")


if __name__ == '__main__':
    app.run(debug=True)