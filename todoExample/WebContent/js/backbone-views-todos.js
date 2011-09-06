var TodoView = Backbone.View.extend({

    //... is a list tag.
    tagName:  "li",

    // Cache the template function for a single item.
    template: _.template($('#item-template').html()),

    // The DOM events specific to an item.
    events: {
      "click .check"              : "toggleDone",
      "click span.todo-edit"      : "edit",
      "click span.todo-destroy"   : "clear",
      "keypress .todo-input"      : "updateOnEnter"
    },

    // The TodoView listens for changes to its model, re-rendering.
    initialize: function() {
    	this.model.bind('change', this.render, this);
	    this.model.bind('destroy', this.remove, this);
	    this.model.bind('editReady', this.editReady, this);
    },
    
    // Re-render the contents of the todo item.
    render: function() {
    	//var date = new Date(this.model.get('startDate'));
    	//console.log(this.model.get('startDate'));
    	//console.log(date.toLocaleString());
    	//console.log(date.getTime());
    	
    	$(this.el).attr('id',this.model.cid);
    	$(this.el).html(this.template(this.model.toJSON()));
    	this.setText();
    	return this;
    },

    // To avoid XSS (not that it would be harmful in this particular app),
    // we use 'jQuery.text' to set the contents of the todo item.
    setText: function() {
      var text = this.model.get('description');
      this.$('.todo-text').text(text);
      this.input = this.$('.todo-input');
      this.input.bind('blur', _.bind(this.close, this)).val(text);
    },

    // Toggle the '"done"' state of the model.
    toggleDone: function() {
      this.model.toggle();
    },

    // Switch this view into '"editing"' mode, displaying the input field.
    edit: function() {
      $(this.el).addClass("editing");
      this.input.focus();
    },

    // Close the '"editing"' mode, saving changes to the todo.
    close: function() {
		
		this.model.save({description: this.input.val()},
				{success: function(model, response) {
							 var errors = new Backbone.Collection(model.get('errors'));
							 errors.each(function(error) {
								showError(new Error(error).get('message'));
							 });
							 
							 if (errors.length == 0) {
								 model.trigger("editReady");
							 }
						  }
				});
    },

    // If you hit 'enter', we're through editing the item.
    updateOnEnter: function(e) {
      if (e.keyCode == 13) this.close();
    },

    // Remove this view from the DOM.
    remove: function() {
      $(this.el).remove();
    },

    // Remove the item, destroy the model.
    clear: function() {
      this.model.destroy();
    },
    
    editReady: function () {
    	$(this.el).removeClass("editing");
    }

});

  // The Application
  // ---------------

  // Our overall **AppView** is the top-level piece of UI.
var AppView = Backbone.View.extend({

    // Instead of generating a new element, bind to the existing skeleton of
    // the App already present in the HTML.
    el: $("#todoapp"),

    // Our template for the line of statistics at the bottom of the app.
    statsTemplate: _.template($('#stats-template').html()),

    // Delegated events for creating new items, and clearing completed ones.
    events: {
      "keypress #new-todo":  "createOnEnter",
      "keyup #new-todo":     "showTooltip",
      "click .todo-clear a": "clearCompleted",
      "click .todo-count a": "moveToToday"
    },

    // At initialization we bind to the relevant events on the 'Todos'
    // collection, when items are added or changed. Kick things off by
    // loading any preexisting todos that might be saved on server.
    initialize: function() {
      this.input    = this.$("#new-todo");

      this.sortedList = this.$('#todo-list')
      						.sortable({update: function(event,ui) {
      								Todos.trigger("orderUpdated");
      							}
      						})
      						.disableSelection();
      //_.bindAll(this, 'render', 'addOne', 'addAll', 'updateOrder');
      
      Todos.bind('add',   this.addOne, this);
      Todos.bind('reset', this.addAll, this);
      Todos.bind('all',   this.render, this);
      Todos.bind('orderUpdated',   this.updateOrder, this);
      Todos.bind('startDateChanged',   this.addOne, this);
      
      Todos.fetch();
    },

    updateOrder: function() {
    	var sort = new TodoSorting();
    	var list=new Array();
    	
    	this.$('#todo-list li').each(function(index) {
    		//Single item update! Todos.getByCid($(this).attr('id')).save({'order':index});
    		list[index] = Todos.getByCid($(this).attr('id')).get('id');
    	});
    	sort.save({'sortedIds':list});
    	
    },
    // Re-rendering the App just means refreshing the statistics -- the rest
    // of the app doesn't change.
    render: function() {
      this.$('#todo-stats').html(this.statsTemplate({
        total:      Todos.length,
        done:       Todos.done().length,
        remaining:  Todos.remaining().length,
        past:       Todos.fromPast().length
      }));
    },

    // Add a single todo item to the list by creating a view for it, and
    // appending its element to the `<ul>`.
    addOne: function(todo) {
    	if (!todo.fromPast()) {
    		// TODO: remove
    		var test = new Dummy();
    		test.save({test: "hello dummy"});
    		
	    	var errors = new Backbone.Collection(todo.get('errors'));
	    	errors.each(function(error) {
	    		showError(new Error(error).get('message'));
	    	});
	    	
	    	var view = new TodoView({model: todo});
	    	this.$("#todo-list").append(view.render().el);
	    	if (errors.length > 0) {
	    		$(view.el).addClass("editing");
	    		
	    		$('.editing .todo-input').focus();
	    		this.showTooltip(e);
	    	}
    	}
    },

    // Add all items in the **Todos** collection at once.
    addAll: function() {
    	Todos.each(this.addOne);
    },

    // If you hit return in the main input field, and there is text to save,
    // create new **Todo** model persisting it to server.
    createOnEnter: function(e) {
	var text = this.input.val();
		if (!text || e.keyCode != 13) return;
		Todos.create({description: text});
		this.input.val('');
		$(".ui-tooltip-top").fadeOut();
	},

    // Clear all done todo items, destroying their models.
    clearCompleted: function() {
	    _.each(Todos.done(), function(todo){ todo.destroy(); });
	    return false;
    },
    
    // Move all todo items from the past to today.
    moveToToday: function() {
    	var d = new Date();
    	
    	_.each(Todos.fromPast(), 
    		  function(todo){ 
    	  		   todo.save({'startDate':d.getTime()},
    	  				 {success: function(model, response) {
    	  					Todos.trigger("startDateChanged", model);
    	  				 }
    			   });
    	  		   
    	  	  });
    	return false;
    },

    // Lazily show the tooltip that tells you to press `enter` to save
    // a new todo item, after one second.
    showTooltip: function(e) {
      var tooltip = this.$(".ui-tooltip-top");
      var val = this.input.val();
      tooltip.fadeOut();
      if (this.tooltipTimeout) clearTimeout(this.tooltipTimeout);
      if (val == '' || val == this.input.attr('placeholder')) return;
      var show = function(){ tooltip.show().fadeIn(); };
      this.tooltipTimeout = _.delay(show, 1000);
    }

});