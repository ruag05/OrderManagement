# OrderManagement
## Steps to Run the application

### Using IDE
1. Clone the repo: git clone https://github.com/ruag05/OrderManagement.git
2. Open the project in an IDE like IntelliJ and run the 'App.java' file
3. Append "src/files/" before the file names:
   - Pass the dataset file. Like "src/files/Dataset - Sheet1.csv"
   - Pass the cards file. Like "src/files/Cards - Sheet1.csv"
   - Pass input files. Like "src/files/Input1 - Sheet1.csv"
     - To pass another input file, choose option 1 by pressing '1' key
     - Keep choosing option '1' for passing input files
4. Exit the application by choosing option 2 by pressing '2' key

## Design Patterns
#### Chain of Responsibility:
- Used the pattern to identify the category of the item<br>
As any Item in the Order/input item list, can belong to any of the categories "Essential", "Luxury", "Misc". So, the ***item can be passed to each category handler sequentially*** to identify whether the item belongs to that category or not. If the item does not belong to that category, it would be passed to next category handler and so on.
- Used the pattern ***to prevent bloating*** of the code<br>
Without this pattern, as the number of item categories grow in future, changing one check for a category can sometimes affect the others. Such a system will become more and more hard to understand and to debug, as the item categories grow.

#### Strategy:

- Implemented the pattern to ***make the application independent/interoperable of the input format***<br>
Currently, the order/items are inputted in *.csv format. However, in future the input could be provided in *.json or *.xml or any other format. So, keeping the future scope of the application in mind, I have implemented the Strategy pattern so that the application can accept the input in any other formats as well.
- Implemented the pattern for logging that is, ***to make application independent of the output format***<br>
As currently, the error is logged in *.txt file and the output is logged in *.csv file, I have implemented the Strategy pattern to make the logging functionality independent of the expected format of the log files. For example, in future the requirement could change such that the error logs are expected in *.xml instead of *.txt and/or the output logs are expected in *.json instead of *.csv. So, implementing the Strategy pattern will enable the application to adhere to the requirement change and allow changing the type of the log file without a problem.

#### Singleton:
- Implemented the pattern ***to ensure that only a single instance of the Database is available*** across the whole application<br>
Since, the database is a ***shared resource***, it will be effective to control access to the database instance.
- Also, since the database instance is ***needed globally*** across the application (like for quantity validation, category-wise validation, adding item to cart, and more) it makes sense to use the Singleton pattern in order to access the database object from anywhere in the application.
- Similarly, applied the design pattern ***to ensure that only a single instance of Card Collection/Card Holder is available*** throughout the application.

## Class Diagram
<img src="screenshots/ClassDiagram.png" width="75%"/>

## Screenshots

### Project Folder Structure
<img src="screenshots/Folder Structure.png" width="75%"/>

### Running the input files in single application excution
#### Terminal
<img src="screenshots/Merged/Terminal.png" width="75%"/>

#### OrderSummary/Output
<img src="screenshots/Merged/Order Summary.png" width="75%"/>

#### ErrorLog
<img src="screenshots/Merged/ErrorLog.png" width="75%"/>

### Running only the Input1 file
#### Terminal
<img src="screenshots/Input1/Terminal.png" width="75%"/>

#### OrderSummary/Output
<img src="screenshots/Input1/OrderSummary.png" width="75%"/>

#### ErrorLog
There was no error while parsing the Input1 only

### Running only the Input2 file
#### Terminal
<img src="screenshots/Input2/Terminal.png" width="75%"/>

#### OrderSummary/Output
<img src="screenshots/Input2/OrderSummary.png" width="75%"/>

#### ErrorLog
<img src="screenshots/Input2/ErrorLog.png" width="75%"/>

### Running only the Input3 file
#### Terminal
<img src="screenshots/Input3/Terminal.png" width="75%"/>

#### OrderSummary/Output
<img src="screenshots/Input3/OrderSummary.png" width="75%"/>

#### ErrorLog
There was no error while parsing the Input1 only

## Output Files

###[ErrorLog.txt](https://drive.google.com/file/d/1jy4WvyXin0vRwFv9F4vftcvg31gwLNXc/view?usp=sharing)
<br>
###[OrderSummary.csv](https://drive.google.com/file/d/149zQdPldX7bFfAVYCUJ0oXNioTcYQieN/view?usp=sharing)

