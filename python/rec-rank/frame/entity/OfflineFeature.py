class OfflineFeature(object):

    def __init__(self,
                 id,
                 feature_name,
                 default_value,
                 value_type,
                 feature_define,
                 expression,
                 details,
                 ctime,
                 mtime,
                 feature_scene,
                 feature_type,
                 feature_status):
        self.id = id
        self.feature_name = feature_name
        self.default_value = default_value
        self.value_type = value_type
        self.feature_define = feature_define
        self.expression = expression
        self.details = details
        self.ctime = ctime
        self.mtime = mtime
        self.feature_scene = feature_scene
        self.feature_type = feature_type
        self.feature_status = feature_status
