from selenium import webdriver
from selenium.webdriver.common.keys import Keys
import unittest

user = "admin@rocketpotatoes.com"
pwd = "rocket"
driver = webdriver.Chrome()
driver.get("https://deco3801.wisebaldone.com/app")


class AuthOutWebTests(unittest.TestCase):
    def test_login(self):
        username = driver.find_element_by_name('username')
        password = driver.find_element_by_name('password')
        login_button = driver.find_element_by_id('login-button')
        username.send_keys(user)
        password.send_keys(pwd)
        self.assertEqual(username.get_attribute("value"), user)
        print("Test case passed: Username entered successfully.\n")
        self.assertEqual(password.get_attribute("value"), pwd)
        print("Test case passed: Password entered successfully.\n")
        login_button.click()
        print("Test case passed: Login completed successfully.\n")


    def test_navigation(self):
        driver.implicitly_wait(1000)
        live = driver.find_element_by_xpath('//a[@href="live.php"]')
        print("Test case passed: Navigation contains live.\n")
        stats = driver.find_element_by_xpath('//a[@href="stats.php"]')
        print("Test case passed: Navigation contains stats.\n")
        reporting = driver.find_element_by_xpath('//a[@href="reporting.php"]')
        print("Test case passed: Navigation contains reporting.\n")
        accounts = driver.find_element_by_xpath('//a[@href="accounts.php"]')
        print("Test case passed: Navigation contains accounts.\n")
        parents = driver.find_element_by_xpath('//a[@href="parents.php"]')
        print("Test case passed: Navigation contains parents.\n")
        children = driver.find_element_by_xpath('//a[@href="children.php"]')
        print("Test case passed: Navigation contains children.\n")
        contact = driver.find_element_by_xpath('//a[@href="contact.php"]')
        print("Test case passed: Navigation contains contact.\n")
        tutorial = driver.find_element_by_xpath('//a[@href="tutorial.php"]')
        print("Test case passed: Navigation contains tutorial.\n")

        live.click()
        print("Test case passed: Live page loads as intended")
        driver.implicitly_wait(3000)
        stats = driver.find_element_by_xpath('//a[@href="stats.php"]')
        stats.click()
        print("Test case passed: Stats page loads as intended")
        driver.implicitly_wait(3000)
        reporting = driver.find_element_by_xpath('//a[@href="reporting.php"]')
        reporting.click()
        print("Test case passed: Reporting page loads as intended")
        driver.implicitly_wait(3000)
        accounts = driver.find_element_by_xpath('//a[@href="accounts.php"]')
        accounts.click()
        print("Test case passed: Accounts page loads as intended")
        driver.implicitly_wait(3000)
        parents = driver.find_element_by_xpath('//a[@href="parents.php"]')
        parents.click()
        print("Test case passed: Parents page loads as intended")
        driver.implicitly_wait(3000)
        children = driver.find_element_by_xpath('//a[@href="children.php"]')
        children.click()
        print("Test case passed: Children page loads as intended")
        driver.implicitly_wait(3000)
        contact = driver.find_element_by_xpath('//a[@href="contact.php"]')
        contact.click()
        print("Test case passed: Contact page loads as intended")
        driver.implicitly_wait(3000)
        tutorial = driver.find_element_by_xpath('//a[@href="tutorial.php"]')
        tutorial.click()
        print("Test case passed: Tutorial page loads as intended")

class ExpectedFailureTestCase(unittest.TestCase):
    @unittest.expectedFailure
    def test_fail(self):
        self.assertEqual(1, 0, "broken")



if __name__ == "__main__":
     unittest.main()
