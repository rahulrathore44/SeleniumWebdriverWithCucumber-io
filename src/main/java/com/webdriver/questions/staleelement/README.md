# Stale Element Reference Exception

A stale element reference exception is thrown in one of two cases.

* The element has been deleted entirely.
* The element is no longer attached to the DOM.

##### The most frequent cause of this is that page that the element was part of has been refreshed.

##### The element was in DOM initially but after modifications in Document Object Model (DOM), the element becomes stale and the StaleElementReferenceException is thrown.

# How to handle it?

* Implement the re-try logic to do a re-lookup of the element in the DOM.


