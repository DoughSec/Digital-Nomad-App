<template>
    <div>
      <!-- Client List Table -->
      <table v-if="$store.state.user.role == 'ROLE_ADMIN'">
        <thead>
          <tr>
            <th>Client ID</th>
            <th>User ID</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Phone Number</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="client in clients" v-bind:key="client.clientId">
            <td>{{ client.clientId }}</td>
            <td>{{ client.userId }}</td>
            <td>{{ client.firstName }}</td>
            <td>{{ client.lastName }}</td>
            <td>{{ client.phoneNumber }}</td>
            <!-- <td>
              <div v-if="client.isEditing" class="flex-container">
                <input v-model="client.firstName" placeholder="First Name" class="flex-item" />
                <input v-model="client.lastName" placeholder="Last Name" class="flex-item" />
                <input v-model="client.phoneNumber" placeholder="xxxxxxxxxx(No spaces/or hyphens)" class="flex-item" />
              </div>
              <div v-else> -->
                <!-- <router-link
                  v-bind:to="{ name: 'client-detail', params: { clientId: client.clientId } }"
                >
                  {{ client }}
                </router-link> -->
              <!-- </div>
            </td> -->
            <!-- <td>
              <button
                v-if="!client.isEditing && $store.state.user.role == 'ROLE_ADMIN'"
                v-on:click="enableEdit(client)"
                title="Edit"
              >
                <i class="fas fa-edit"></i>
                Edit
              </button>
              <button v-if="client.isEditing" v-on:click="saveEdit(client)">
                Save
              </button>
              <button v-if="client.isEditing" v-on:click="cancelEdit(client)">
                Cancel
              </button>
            </td> -->
          </tr>
        </tbody>
      </table>

      <h1 v-if="$store.state.user.role == 'ROLE_ADMIN' || $store.state.user.role == 'ROLE_USER'">Client Registration</h1>  
      <!-- New Client Form -->
      <form id="clientForm" v-if="$store.state.user.role == 'ROLE_ADMIN'  || $store.state.user.role == 'ROLE_USER'" v-on:submit.prevent="addClient">    
        <div class="flex-container">             
          <label for="client">First Name: </label>   
          <input class="flex-item"
            type="text"
            v-model="newClient.firstName"
            id="firstName"
            placeholder="First Name"
            required
            autocomplete="off"
          />  
          
          <label for="client">Last Name: </label>   
          <input class="flex-item"
            type="text"
            v-model="newClient.lastName"
            id="lastName"
            placeholder="Last Name"
            required
            autocomplete="off"
          />  
          
          <label for="client">Phone Number: </label>   
          <input class="flex-item"
            type="text"
            v-model="newClient.phoneNumber"
            id="phoneNumber"
            placeholder="xxxxxxxxxx(No spaces/or hyphens)"
            required
            autocomplete="off"
          />  
         <input type="submit" value="Register" class="flex-item btn btn-primary btn-sm" v-on:click="addClient"/>
       
         </div>
      </form>
  
    </div>
</template>
  
<script>
    import clientsService from '../services/ClientService.js';
    
    export default {
    name: 'ClientsList',
    data() {
        return {       
        clientsList: [],                      
        newClient: {
            clientId: 0,
            userId: 0,
            firstName: '',
            lastName: '',
            phoneNumber: ''
        }
        };
    },
    
    computed:{       
        clients(){
            return this.$store.state.clients;
        }
    },
    methods: {
        addClient() {
        const client = {
            firstName: this.newClient.firstName,
            lastName: this.newClient.lastName,
            phoneNumber: this.newClient.phoneNumber,
            isEditing: false,
        };
        //Use client to create new client.
        clientsService.createClient(client)
            .then(response => {
            if (response.status >= 201) {
                // Get clients and set clients
                this.getAllClients();
                alert('Successfully Registered As Client');
                // Reset form
                this.resetForm();
            }
            })
            .catch(error => {
                if (error.response) {
                    alert(`Error adding new client. Response received was "${error.response.statusText}".`, error);
                } else if (error.request) {                
                    alert('Error adding new client. Server could not be reached.', error);
                } else {
                    alert('Error adding new client(Might still be a success, Phone Validation API use limit has been reached). Request might not be created.', error);
                }
            });
        },
        enableEdit(client) {
            // Save original values to allow canceling the edit
            client.originalFirstName = client.firstName;
            client.originalLastName = client.lastName;
            client.originalPhoneNumber = client.phoneNumber;
            client.isEditing = true;
        },
        saveEdit(client) {
          clientsService.updateClient(client.clientId, client)
            .then(response => {
                if (response.status === 200 || response.status === 201) {
                    // Get clients and set clients
                    this.getAllClients();
                }
            })
            .catch(error => {
                if (error.response) {
                    console.error(`Error updating client. Response received was "${error.response.statusText}".`, error);
                } else if (error.request) {                
                    console.error('Error updating client. Server could not be reached.', error);
                } else {
                    console.error('Error updating client. Request could not be created.', error);
                }
            })
            .finally(() => {
                client.isEditing = false;
                delete client.originalFirstName;
                delete client.originalLastName;
                delete client.originalPhoneNumber;
            });
        },

        cancelEdit(client) {
            // Revert to original values and exit edit mode
            client.originalFirstName = client.firstName;
            client.originalLastName = client.lastName;
            client.originalPhoneNumber = client.phoneNumber;
            client.isEditing = false;
            delete client.originalFirstName;
            delete client.originalLastName;
            delete client.originalPhoneNumber;
        },  
        getClients() {
        
        clientsService.getAllClients()
            .then(response => {
                this.clientsList = response.data;
                this.setClients(this.clientsList);
            })
            .catch(error => {
                if (error.response) {
                console.error(`Error getting clients. Response received was "${error.response.statusText}".`, error);
            } else if (error.request) {                
                console.error('Error getting clients. Server could not be reached.', error);
            } else {
                console.error('Error getting clients. Request could not be created.', error);
            }
            });
        },
        setClients(clients) {
            this.$store.commit('SET_CLIENTS', clients);
        },
        resetForm() {
            this.newClient.originalFirstName = '';
            this.newClient.originalLastName = '';
            this.newClient.originalPhoneNumber = '';
        },
    },
    };
</script>


<style scoped>
    table {
    margin: auto;
    width: 90vw;
    }

    th,
    td {
    text-align: left;
    padding: 10px;
    vertical-align: top;
    }

    tr:nth-child(even) {
    background-color: rgb(238, 238, 238);
    }

    button {
    background: none;
    border: none;
    cursor: pointer;
    padding: 5px;
    }

    button:hover {
    opacity: 0.7;
    }

    form {
    margin-bottom: 20px;
    width: 90vw;
    }

    form div {
    display: flex;
    margin: auto;
    flex-direction: row;
    flex-wrap: wrap;
    }

    form label {
    margin-right: 10px;
    }
    input{
    margin-right: 5px;
    }
    textarea{
    margin-right: 5px;
    resize:none;
    height: 40px;
    }
    textarea.placeholder{
    text-align: left;
    vertical-align: middle;
    }

    .flex-container{
    display: flex;
    align-content: flex-start;
    flex-direction: row;
    flex-wrap: wrap;
    
    }
    .flex-item {
        /* Sets flex-grow, flex-shrink, and flex-basis */ 
    flex: 1 1 1;               
    text-align: center;
    }

</style>
