from common.Client import Client
from common.URL import *


class ProductController:
    @staticmethod
    def deleteById(id):
        response = Client.custom_request('GET', BASE_URL + API_PREFIX + DELETE_PRODUCT_REQUEST, params={'id': id})
        return response.json()

    @staticmethod
    def create(params):
        response = Client.custom_request('POST', BASE_URL + API_PREFIX + CREATE_PRODUCT_REQUEST, json=params)
        return response.json()

    @staticmethod
    def edit(params):
        response = Client.custom_request('POST', BASE_URL + API_PREFIX + EDIT_PRODUCT_REQUEST, json=params)
        return response.json()

    @staticmethod
    def getAll():
        response = Client.custom_request('GET', BASE_URL + API_PREFIX + GET_ALL_PRODUCTS_REQUEST)
        return response.json()
