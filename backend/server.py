import os

import numpy as np
from flask import (Flask, Response, jsonify, request, send_file,
                   send_from_directory)
from PyPDF2 import PageObject, PdfReader, PdfWriter

UPLOAD_FOLDER = './/backend//documents//'

app = Flask(__name__)
app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER

def modifyPDF(file_path, name):
    reader = PdfReader(file_path)
    writer = PdfWriter()

    #Iterate through pages and modify
    for i in range(reader.numPages):
        current_page = reader.getPage(i)

        key_word_to_search = ['Terms', 'Conditions']
        user_data = {'Terms': 'Tudor', 'Conditions': 'Matei'}
        page_text = current_page.extract_text() #str object

        for word in key_word_to_search:
            #Currently only finds one word then stops (I need it to find all words in the string)
            first_index_of_word = page_text.find(word)

            wordLength = len(word)
            last_indfex_of_word = first_index_of_word + wordLength
            #print(first_index_of_word)
            #print(last_indfex_of_word)
            print(user_data[word])
            page_text = page_text[:first_index_of_word] +  f'{user_data[word]}' +  page_text[last_indfex_of_word:]
            print(page_text)

        print("===================")
        print("Content on page:" + str(i + 1))
        print("===================")
        print(page_text)


        writer.addPage(current_page)

    #Write final modified pdf
    write_path = './/backend//output//' + name 
    with open(write_path, mode="wb") as output_file:
        writer.write(output_file)

@app.route('/')
def index():
    return '<h1>FLASK APP IS RUNNING!</h1>'


@app.route('/api/pass', methods=['POST'])
def loadPDF():
    pdf_file = request.files['doc']
    pdf_name = pdf_file.filename

    pdf_file.save(os.path.join(app.config['UPLOAD_FOLDER'] + pdf_name))

    file_path = os.path.join(app.config['UPLOAD_FOLDER'] + pdf_name)
    
    modifyPDF(file_path, pdf_name)

    return send_file('documents//' + pdf_name)

if __name__ == '__main__':
    app.run(debug=True)