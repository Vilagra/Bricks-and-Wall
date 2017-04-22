Wall and Bricks Application
 
Development environment:  Android studio

Work description:                                                         
     During the start a layout for data entry and result displays on the main screen: two Edit Text views for entering wall sizes, two Edit Text views for entering width and quantity of bricks, as well as three buttons that are initially disabled. The Add button is enabled when the width of the bricks fields and the quantity are filled. When you click it, the width of this brick type and the amount of such bricks are added to the list that appears below. The Clear button is enabled when at least one of the Edit views is filled or the list is not empty. When clicked it clears all the Edit views and the list. The Verification button is enabled if the wall dimensions are entered and at least one type of bricks is added to the list. When clicked it starts calculation to determine if the bricks specified in the list can fit in the wall specified in the Edit view. The result is displayed in the Yes (if the bricks can fit in the wall) and No (if not) dialog box. After pressing the Cancel button the dialog box closes and you return to the initial window. From here you can enter new data. When you add the first brick type the list of bricks appears, which is updated every time you add the new brick type. List items have a Delete button, which when clicked deletes a list item. When you add a new brick type which is already in the list, the old value is deleted, the new one is recorded.

Technical peculiarities:                                                                        
     It's indicated in the task that the result is output in the Text view, I decided that with the layout I use it's more logical to display the result in the dialog. Since the wall dimensions and the number of bricks are not limited, and with a large amount of data the calculation can take a certain amount of time, I decided to do the calculation asynchronously, for this I used the AsyncTaskLoader. In the process of calculation, the progress dialog is displayed. For landscape orientation on mobile phones I wrote a more compressed layout, which allows a Recycler view to fit the screen.
