# OrderManagement
## Steps to Run the application
1. Clone the repo: git clone https://github.com/ruag05/OrderManagement.git
2. Navigate to source folder: cd "OrderManagement/src"
3. Compile the java project: javac -d . App.java
4. Run the java application: java App
5. Pass the dataset and input files: files/Dataset.csv files/Sample Input File.csv

## Design Patterns
- Chain of Responsibility: Used this pattern as any Item in the Order/input item list, can belong to any of the categories "Essential", "Luxury", "Misc". 
  - So, the ***item can be passed to each category handler sequentially*** to identify whether the item belongs to that category or not. If the item does not belong to that category, it would be passed to next category handler and so on.
  - In future, ***as the number of item categories grow in future implementing Chain of Responsibility pattern will be useful***. Without this pattern, the code can become more and more bloated as more categories gets added.
    - Also, with growing number of categories, changing one check for a category can sometimes affect the others. Such a system will be hard to understand and also hard to debug.
- Strategy: Implemented the Strategy pattern to ***make the application independent/interoperable of the input format***.
  - Currently, the order/items are inputted in *.csv format. However, in future the input could be provided in *.json or *.xml or any other format. So, keeping the future scope of the application in mind, I have implemented the Strategy pattern so that the input can be provided in any other formats
- Singleton: Implemented Singleton design pattern to ensure that only a single instance of the Database is available across the whole application. 
  - Since, the database is a ***shared resource***, it will be effective to control access to the database instance.
  - Also, since the database instance is ***needed globally*** across the application (like for quantity validation, category-wise validation, adding item to cart, and more) it makes sense to use the Singleton pattern to the database object from anywhere in the application.

## Class Diagram
