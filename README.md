# StockExchangeGame
# Description
This project is basically just a custom implementation of a stock market.<br>
Stocks can be bought and sold.<br>
Stocks have a tendency for their price going up or down.<br>
You always have the option to wait a few days(by executing a command) which will change all the stocks prices.<br>
After the user decides to quit, the program will generate diagrams of the price changes of the stocks.

# Use Cases

| Use Case: 1 | Buying a Stock |
| --- | --- |
| Description | The user can buy a Stock for a certain amount of money |
| Pre-condition | The user has enough money to buy the stock |
| Post-condition | The bought stock has been added to the user&#39;s inventory and the price has been deducted from the users account |
| Exceptions | If the user does not have enough money to buy his selected Stock, he will be notified that he does not have enough money |

| Use Case: 2 | Selling a Stock |
| --- | --- |
| Description | The user can sell a Stock for a certain amount of money |
| Pre-condition | The user has a stock |
| Post-condition | The user&#39;s sold stock has been removed from the users inventory.The current price of the stock has been added to the user&#39;s account. |
| Exceptions | If the user tries to sell more units of a stock than he has, he will be informed that that is not possible |

| Use Case: 3 | Waiting |
| --- | --- |
| Description | The user can wait for x days. This will make the Stocks prices/values change either up or down |
| Pre-condition | none |
| Post-condition | The Stocks values has changed X times |
| Exceptions | None |

# Use case diagram

![](https://github.com/EnderDark1010/StockExchange/blob/main/picsForReadMe/ucd.png)

# CRC Cards

![](https://github.com/EnderDark1010/StockExchange/blob/main/picsForReadMe/crcc.png)

# Domain Model

![](https://github.com/EnderDark1010/StockExchange/blob/main/picsForReadMe/dm.png)

# Class Diagram

![](https://github.com/EnderDark1010/StockExchange/blob/main/picsForReadMe/CD.jpg)
