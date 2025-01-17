/**
 * Import function triggers from their respective submodules:
 *
 * import {onCall} from "firebase-functions/v2/https";
 * import {onDocumentWritten} from "firebase-functions/v2/firestore";
 *
 * See a full list of supported triggers at https://firebase.google.com/docs/functions
 */

import * as functions from "firebase-functions/v2";
import * as logger from "firebase-functions/logger";
import * as admin from "firebase-admin";


// Start writing functions
// https://firebase.google.com/docs/functions/typescript

// export const helloWorld = onRequest((request, response) => {
//   logger.info("Hello logs!", {structuredData: true});
//   response.send("Hello from Firebase!");
// });

admin.initializeApp();

/**
 * Represents a single item in a list.
 */
class Item {
    name: string;
    qtd: number;
    checked: boolean;

    /**
     * Creates an instance of an item.
     * @param name - The name of the item.
     * @param qtd - The quantity of the item.
     * @param checked - Whether the item is checked.
     */
    public constructor(name: string, qtd: number, checked: boolean) {
        this.name = name;
        this.qtd = qtd;
        this.checked = checked;
    }
}

class ListItems {

    name: string | null;
    owners: string[] | null;
  
    constructor( name: string | null = null, owners: string[] | null = null) {
   
      this.name = name;
      this.owners = owners;
    }
  }

  // Firestore data converter
const listItemsConverter = {
    toFirestore: (items: ListItems) => {
        return {
            name : items.name,
            owners : items.owners
        };
    },
    fromFirestore: (snapshot: any, options: any) => {
        const data = snapshot.data(options);
        return new ListItems(
            data.name,
            data.owners
        );
    }
};

const itemConverter = {
    toFirestore: (item: Item) => {
        return {
            name : item.name,
            qtd : item.qtd,
            checked : item.checked,
        };
    },
    fromFirestore: (snapshot: any, options: any) => {
        const data = snapshot.data(options);
        return new Item(
            data.name,
            data.qtd,
            data.checked
        );
    }
};

export const sendNotification = functions.firestore
    .onDocumentCreated("lists/{listId}/items/{itemId}", (event) => {
        //const newValue = event;

        var item = itemConverter.fromFirestore(event.data, {});

        logger.info("New notification: ", item.name);

        var listId = event.params.listId;

        admin.firestore().collection("lists").doc(listId).get().then( (listItemsData) => {
            var listItems = listItemsConverter.fromFirestore(listItemsData.data, {});

            listItems.owners?.forEach( userId => {
                admin.firestore().collection("users").doc(userId).collection("fcd").get().then((fcd) => {

                    fcd.forEach((doc) => {
                        logger.info("Send notification to: ", doc.data().token);
                        admin.messaging().sendToDevice(doc.data().token, {
                            notification: {
                                title: "New item on the list",
                                body: item.name,
                                sound: "default",
                                click_action: "new_item"
                            },
                            data: {
                                action: "new_item",
                                docPath: "lists/${listId}/items/${itemId}",
                            }
                        }).then((response) => {
                            logger.info("Successfully sent message:", response);
                        }).catch((error) => {
                            logger.info("Error sending message:", error);
                        });
                    });
                });
            });
        });
        
    });