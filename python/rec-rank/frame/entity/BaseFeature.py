class BaseFeature(object):

    def __init__(self,
                 id,
                 feature_mark,
                 status,
                 feature_name,
                 feature_source,
                 expression,
                 feature_details,
                 ctime,
                 mtime,
                 online_status,
                 feature_type,
                 feature_value_type,
                 d_value_str,
                 d_value_type,
                 feature_scene):
        self.id = id
        self.feature_mark = feature_mark
        self.status = status
        self.feature_name = feature_name
        self.feature_source = feature_source
        self.expression = expression
        self.feature_details = feature_details
        self.ctime = ctime
        self.mtime = mtime
        self.online_status = online_status
        self.feature_type = feature_type
        self.feature_value_type = feature_value_type
        self.d_value_str = d_value_str
        self.d_value_type = d_value_type
        self.feature_scene = feature_scene
