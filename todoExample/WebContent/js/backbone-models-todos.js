var Error = Backbone.Model.extend({});
var TodoOrder = Backbone.Model.extend({urlRoot : '/services/restbroker/todos/sort'});
var Todo = Backbone.Model.extend({
	// Default attributes for a todo item.
    defaults: function() {
      return {
    	done:  0,
        order: Todos.nextOrder()
      };
    },
    // Toggle the 'done' state of this todo item.
    toggle: function() {
      toggle = this.get("done")==0 ? 1 : 0;
      this.save({done: toggle});
    }

});

var TodoList = Backbone.Collection.extend({

    // Reference to this collection's model.
    model: Todo,

    // Base url for rest based service todos.
    url: '/services/restbroker/todos',
    parse: function(response) {
    	return response.todos;
    },
    
    // Filter down the list of all todo items that are finished.
    done: function() {
      return this.filter(function(todo){ return todo.get('done') == 1; });
    },

    // Filter down the list to only todo items that are still not finished.
    remaining: function() {
      return this.without.apply(this, this.done());
    },

    // We keep the Todos in sequential order, This generates the next order number for new items.
    nextOrder: function() {
      if (!this.length) return 1;
      return this.last().get('order') + 1;
    },

    // Todos are sorted by their original insertion order.
    comparator: function(todo) {
      return todo.get('order');
    }

});

//Create our global collection of **Todos**.
var Todos = new TodoList;
