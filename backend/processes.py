from PyPDF2 import PageObject, PdfReader, PdfWriter


def modifyPDF(file_path, name):
        reader = PdfReader(file_path)
        writer = PdfWriter()

        key_word_to_search = ['Data', 'Semnătura']
        user_data = {'Data': '01.01.2023', 'Semnătura': 'Matei'} 

        #Iterate through pages and modify
        for i in range(reader.numPages):
            current_page = reader.getPage(i)

            page_text = current_page.extract_text() #str object

            res = page_text.split()
            
            #for word in key_word_to_search:

            # for word in key_word_to_search:
            #     #Currently only finds one word then stops (I need it to find all words in the string)
            #     list_of_last_indexes = []
            #     while page_text.find(word) != -1:
            #         first_index_of_word = page_text.find(word)
            #         wordLength = len(word)
            #         list_of_last_indexes.append(first_index_of_word + wordLength)

            #     for index in range(len(list_of_last_indexes)):
            #         page_text = page_text[:list_of_last_indexes[index]] +  f' {user_data[word]}' +  page_text[list_of_last_indexes[index]:]
                
            #     # #print(first_index_of_word)
            #     # #print(last_indfex_of_word)
            #     # print(user_data[word])
                
            #     # print(page_text)

            print("===================")
            print("Content on page:" + str(i + 1))
            print("===================")
           # print(page_text)


            writer.addPage(current_page)

        #Write final modified pdf
        write_path = './/backend//output//' + name 
        with open(write_path, mode="wb") as output_file:
            writer.write(output_file)