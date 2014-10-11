/*global Backbone */
var app = app || {};

(function () {
	'use strict';

	// Todo Model
	// ----------

	// Our basic **Todo** model has `title`, `order`, and `done` attributes.
	app.Todo = Backbone.Model.extend({
		// Default attributes for the todo
		// and ensure that each todo created has `title` and `done` keys.
		defaults: {
			title: '',
			done: false
		},

		// Toggle the `done` state of this todo item.
		toggle: function () {
			this.save({
				done: !this.get('done')
			});
		}
	});
})();
