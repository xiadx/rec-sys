from django.urls import path

from . import views

urlpatterns = [
    path('', views.index, name='index'),
    path('feature-importance/', views.feature_importance, name='feature_importance'),
    path('trees-graphviz/', views.trees_graphviz, name='trees_graphviz'),
    path('shap-explain/outline/', views.shap_explain_outline, name='shap_explain_outline'),
    path('shap-explain/summary/', views.shap_explain_summary, name='shap_explain_summary'),
    path('shap-explain/single/', views.shap_explain_single, name="shap_explain_single"),
    path('lime-explain/', views.lime_explain, name='lime_explain'),
]
