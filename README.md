# StockExchangeGame
# Documentation LB2

Manuel Andres

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

![](RackMultipart20220329-4-1nsfxyo_html_8842a75d4e8f7180.png)

# CRC Cards

![](RackMultipart20220329-4-1nsfxyo_html_f900871ea35cb010.png)

# Domain Model

![](RackMultipart20220329-4-1nsfxyo_html_3c79f7eeea292924.png)
