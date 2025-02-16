BBC News Reader Overview BBC News Reader is an Android app that fetches and displays news articles from the BBC RSS feed. Users can browse, view details, save favorites, and customize their experience.

Features

News List & Details: View article titles in a list. Select an article to see its details (title, description, date, link). Favorites Management: Add articles to favorites and remove them. Favorites persist across sessions. Search: Search articles by title or keywords. Notifications: Toast for saving articles and Snackbar for deletions. Help Menu: Instructions available via AlertDialog in each activity. Multilingual Support: English and Canadian French. Customization: Save settings (e.g., theme, language) using SharedPreferences.

Activities: Main Activity: News list Detail Activity: Article details Favourites Activity: Saved articles Preference Activity: App settings Toolbar & Navigation Drawer: Quickly switch between activities. Data & Networking

Persistent Storage: Save articles to a database; favorites reappear after relaunch.

RSS Feed Parsing: Uses AsyncTask to fetch articles from: http://feeds.bbci.co.uk/news/world/us_and_canada/rss.xml.

Open Links in Browser: View article links in the device’s web browser.

Installation Clone the repo: git clone https://github.com/daoudab/Androidbbc.git Open in Android Studio and run on a device or test on a physical devices. Project Owner Daoud Contributions welcome via pull requests.

# BBC News Reader

![Video Preview](https://raw.githubusercontent.com/daoudab/Androidbbc/master/ezgif-2-3fb41d7a9a.gif)

[Watch the full video](https://raw.githubusercontent.com/daoudab/Androidbbc/master/WhatsApp%20Video%202025-01-24%20at%2010.08.34%20AM.mp4)


